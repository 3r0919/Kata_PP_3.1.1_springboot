package web.springboot_pp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.springboot_pp.model.User;
import web.springboot_pp.service.UserService;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

	private final UserService userService;

	@Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
	public String showUsers(ModelMap model) {
		model.addAttribute("user", userService.getAllUsers());
		return "show";
	}

	@GetMapping(value = "/new")
	public String showNewUserForm(Model model) {
		model.addAttribute("user", new User());
		return "add";
	}

	@PostMapping(value="/new")
	public String addUser(@ModelAttribute("user") @Validated User user, BindingResult result) {
		if (result.hasErrors()) {
			return "add";
		}
		userService.addUser(user);
		return "redirect:/users";
	}

	@GetMapping(value = "/edit")
	public String getEditUserForm(@RequestParam("id") Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "edit";
	}

	@PostMapping(value = "/edit")
	public String editUser(@RequestParam("id") Long id, @ModelAttribute("user") @Validated User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "edit";
		}
		User oldUser = userService.getUserById(id);
		oldUser.setName(user.getName());
		oldUser.setSurname(user.getSurname());
		oldUser.setAge(user.getAge());
		userService.editUser(oldUser);
		return "redirect:/users";
	}

	@GetMapping(value = "/delete")
	public String getDeleteUserForm(@RequestParam("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}
}