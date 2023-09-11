package com.example.arul.ProjectAuth.Controller;

import com.example.arul.ProjectAuth.Dto.AuthRequest;
import com.example.arul.ProjectAuth.Entity.UserEntity;
import com.example.arul.ProjectAuth.Service.JwtService;
import com.example.arul.ProjectAuth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ParentController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;
    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/registration")
    public String newRegister(){
        return "registerPage";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/register")
    public String sendDetails(@ModelAttribute("user") UserEntity entity){
        service.saveUser(entity);
        return "success";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/all")
    @ResponseBody
    public String getAll(){
        return "Success Buddy...!";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user/login")
    public String userLogin(){
        return "loginPage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user/loginSuccess")
    public String logSuccess(Authentication authentication, Model model){
        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName();
            model.addAttribute("username", username);

            return "logSuccess";
        }
        else {
            return "redirect:/login";
        }
    }

//    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/token/tokenpage")
    public String usertoken(){
        return "tokenPage";
    }

    @PostMapping("/token/authenticate")
    @ResponseBody
    public String GenerateAndValidateToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));

        if(authentication.isAuthenticated()){
            String genToken = jwtService.generateToken(authRequest.getUsername());
            return "Generate Token : " + genToken;
        }
        else {
            throw new UsernameNotFoundException("Ivalid User Request...!");
        }


    }




}
