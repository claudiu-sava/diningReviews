package com.claudiusava.diningReviews.controller;
import com.claudiusava.diningReviews.Check;
import com.claudiusava.diningReviews.model.Review;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    private String showUsersPage(Model model,
                                 @CookieValue("username") String username,
                                 @RequestParam Optional<String> sortBy){


        if(sortBy.isPresent()){
            List<User> users = userRepository.findAllBy(Sort.by(sortBy.get()));
            model.addAttribute("users", users);
        } else {
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
        }

        model.addAttribute("username", username);
        model.addAttribute("title", "Users");
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
           userRepository.save(user);
           return "redirect:/login";
       }
       model.addAttribute("error", "Error while creating user");
       return ErrorController.error(model);

   }


   @GetMapping("/search")
   private String getUser(@RequestParam String username,
                          @CookieValue(value = "username", defaultValue = "None") String loggedUser,
                          Model model){

       if (loggedUser.equals("None")) {
           model.addAttribute("error", "Please login before searching for other users.");
           return ErrorController.error(model);
       }

       Optional<User> userOptional = userRepository.findByUsername(username);
       if (!userOptional.isPresent()){
           model.addAttribute("error", "The user you are searching for doesn't exist");
           return ErrorController.error(model);
       }

       User user = userOptional.get();
       model.addAttribute("user", user);
       model.addAttribute("title", user);
       return "showUser";
   }

   @GetMapping("/edit")
   private String showEditUserPage(Model model,
                                   @CookieValue(value = "username", defaultValue = "None") String username){

        User user = userRepository.findByUsername(username).get();
        model.addAttribute("user", user);
        model.addAttribute("title", "Edit User: " + user.getUsername());
        return "editUser";
   }

   @PostMapping("/edit")
    private String editUser(@ModelAttribute User user,
                            @CookieValue(value = "username", defaultValue = "None") String loggedUser){


       Optional<User> userOptional = userRepository.findByUsername(loggedUser);
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

       return "redirect:/users/search?username=" + loggedUser;
   }

}
