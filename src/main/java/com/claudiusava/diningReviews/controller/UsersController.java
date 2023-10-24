package com.claudiusava.diningReviews.controller;
import com.claudiusava.diningReviews.Check;
import com.claudiusava.diningReviews.model.Role;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.RoleRepository;
import com.claudiusava.diningReviews.repository.UserRepository;
import com.claudiusava.diningReviews.services.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    private String showUsersPage(Model model,
                                 @RequestParam Optional<String> sortBy){


        if(sortBy.isPresent()){
            List<User> users = userRepository.findAllBy(Sort.by(sortBy.get()));
            model.addAttribute("users", users);
        } else {
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
        }

        model.addAttribute("username", UserDetail.getLoggedUserUsername());
        model.addAttribute("title", "Users");
        System.out.println(UserDetail.getLoggedUserRole());
        return "users";
    }

    @GetMapping("/new")
    private String showNewUserPage(Model model){
        model.addAttribute("title", "New User");
        return "newUser";
    }

   @PostMapping("/new")
    private String addNewUser(@ModelAttribute User user,
                              Model model){

       if(!Check.usernameExists(user.getUsername(), userRepository)){
           User userToDb = new User();
           userToDb.setUsername(user.getUsername());
           userToDb.setPassword(passwordEncoder.encode(user.getPassword()));
           userToDb.setCity(user.getCity());
           userToDb.setKanton(user.getKanton());
           userToDb.setZip(user.getZip());
           userToDb.setIsPeanutAllergic(user.getIsPeanutAllergic());
           userToDb.setIsEggAllergic(user.getIsEggAllergic());
           userToDb.setIsDiaryAllergic(user.getIsDiaryAllergic());

           Role roles = roleRepository.findByRoleName("ROLE_USER").get();
           userToDb.setRoles(Collections.singleton(roles));

           userRepository.save(userToDb);
           return "redirect:/login";

       }
       model.addAttribute("error", "Error while creating user");
       return ErrorController.error(model);

   }


   @GetMapping("/search")
   private String getUser(@RequestParam String username,
                          Model model){

       if (UserDetail.getLoggedUserUsername() == null) {
           model.addAttribute("error", "Please login before searching for other users.");
           return ErrorController.error(model);
       }

       Optional<User> userOptional = userRepository.findByUsername(username);
       if (!userOptional.isPresent()){
           model.addAttribute("error", "The user you are searching for doesn't exist");
           return ErrorController.error(model);
       }

       User user = userOptional.get();
       for(Role role : user.getRoles()){
           if(role.getRoleName().equals("ROLE_ADMIN")){
               model.addAttribute("isAdmin", "true");
           } else {
               model.addAttribute("isAdmin", "false");
           }
       }
       model.addAttribute("user", user);
       model.addAttribute("title", user);
       return "showUser";
   }

   @GetMapping("/edit")
   private String showEditUserPage(Model model){

        User user = userRepository.findByUsername(UserDetail.getLoggedUserUsername()).get();
        model.addAttribute("user", user);
        model.addAttribute("title", "Edit User: " + user.getUsername());
        return "editUser";
   }

   @PostMapping("/edit")
    private String editUser(@ModelAttribute User user){


       Optional<User> userOptional = userRepository.findByUsername(UserDetail.getLoggedUserUsername());
       User userToUpdate = userOptional.get();

       if(user.getCity() != null){
           userToUpdate.setCity(user.getCity());
       }
       if(user.getKanton() != null){
           userToUpdate.setKanton(user.getKanton());
       }
       if(user.getZip() != null){
           userToUpdate.setZip(user.getZip());
       }
       if(user.getIsPeanutAllergic() != null){
           userToUpdate.setIsPeanutAllergic(user.getIsPeanutAllergic());
       }
       if(user.getIsEggAllergic() != null){
           userToUpdate.setIsEggAllergic(user.getIsEggAllergic());
       }
       if(user.getIsDiaryAllergic() != null){
           userToUpdate.setIsDiaryAllergic(user.getIsDiaryAllergic());
       }

       userRepository.save(userToUpdate);

       return "redirect:/users/search?username=" + UserDetail.getLoggedUserUsername();
   }

}
