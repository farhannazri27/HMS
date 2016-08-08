package com.onsemi.hms.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.hms.dao.MenuDAO;
import com.onsemi.hms.dao.UserDAO;
import com.onsemi.hms.dao.UserGroupAccessDAO;
import com.onsemi.hms.dao.UserGroupDAO;
import com.onsemi.hms.model.Menu;
import com.onsemi.hms.tools.QueryResult;
import com.onsemi.hms.model.User;
import com.onsemi.hms.model.UserGroup;
import com.onsemi.hms.model.UserGroupAccess;
import com.onsemi.hms.model.UserSession;
import com.onsemi.hms.tools.SpmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin")
@SessionAttributes({"userSession"})
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Model model) {
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(
            Model model,
            @RequestParam(required = false) String selectedGroup
    ) {
        selectedGroup = SpmlUtil.nullToEmptyString(selectedGroup);
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getUserList(selectedGroup);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(selectedGroup);
        model.addAttribute("userList", userList);
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("selectedGroup", selectedGroup);
        return "admin/user";
    }

    @RequestMapping(value = "/user/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String userAdd(
            Model model
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        return "admin/user_add";
    }

    @RequestMapping(value = "/user/loginid/{loginId}", method = RequestMethod.GET)
    @ResponseBody
    public String userLoginId(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByLoginId(loginId);
        if (user == null) {
            return "false";
        } else {
            return "true";
        }
    }

    @RequestMapping(value = "/user/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByLoginId(loginId);
        if (user == null) {
            user = new User();
            user.setLoginId(loginId);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setGroupId(groupId);
            user.setCreatedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.insertUser(user);
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("admin.label.user.save.error", args, locale));
                model.addAttribute("loginId", loginId);
                model.addAttribute("fullname", fullname);
                model.addAttribute("email", email);
                UserGroupDAO userGroupDAO = new UserGroupDAO();
                List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
                model.addAttribute("userGroupList", userGroupList);
                return "admin/user_add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.save.success", args, locale));
                return "redirect:/admin/user/edit/" + queryResult.getGeneratedKey();
            }
        } else {
            args = new String[1];
            args[0] = loginId;
            model.addAttribute("error", messageSource.getMessage("general.label.exist.success", args, locale));
            model.addAttribute("loginId", loginId);
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
            model.addAttribute("userGroupList", userGroupList);
            return "admin/user_add";
        }
    }

    @RequestMapping(value = "/user/edit/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userEdit(
            Model model,
            @PathVariable("userId") String userId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(userId);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(user.getGroupId());
        model.addAttribute("user", user);
        model.addAttribute("userGroupList", userGroupList);
        return "admin/user_edit";
    }

    @RequestMapping(value = "/user/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String userUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String isActive
    ) {
        User user = new User();
        user.setId(userId);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setIsActive(isActive);
        user.setModifiedBy(userSession.getId());
        user.setGroupId(groupId);

        UserDAO userDAO = new UserDAO();
        QueryResult queryResult = userDAO.updateUser(user);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/password", method = {RequestMethod.GET, RequestMethod.POST})
    public String userPassword(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmPassword
    ) {
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getUser(userId);
        if (BCrypt.checkpw(currentPassword, currentUser.getPassword())) {
            User user = new User();
            user.setId(userId);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setModifiedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.updatePassword(user);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.password.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.password.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.current_password.error", args, locale));
        }

        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/delete/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("userId") String userId
    ) {
        UserDAO userDAO = new UserDAO();
        QueryResult queryResult = userDAO.deleteUser(userId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.delete.error", args, locale));
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String group(
            Model model
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        return "admin/group";
    }

    @RequestMapping(value = "/group/menu/{groupId}", method = RequestMethod.GET)
    public String groupMenu(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupAccessDAO userGroupAccessDAO = new UserGroupAccessDAO();
        List<UserGroupAccess> userGroupAccessList = userGroupAccessDAO.getUserGroupAccess(groupId);
        model.addAttribute("userGroupAccessList", userGroupAccessList);
        model.addAttribute("groupId", groupId);
        return "admin/group_menu";
    }

    @RequestMapping(value = "/group/menu/save", method = {RequestMethod.POST})
    public String groupMenuSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String[] groupAccess
    ) {
        groupAccess = SpmlUtil.nullToEmptyString(groupAccess);
        UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
        //should use batch insert for performance
        QueryResult addQueryResult = new QueryResult();
        addQueryResult.setResult(0);
        for (String access : groupAccess) {
            UserGroupAccessDAO addUserGroupAccessDAO = new UserGroupAccessDAO();
            addQueryResult = addUserGroupAccessDAO.addAccess(groupId, access);
        }
        QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccess(groupId, groupAccess);
        int result = addQueryResult.getResult() + remQueryResult.getResult();
        if (result != 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.access.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.access.error", args, locale));
        }
        return "redirect:/admin/group/menu/" + groupId;
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String groupAdd(Model model) {
        return "admin/group_add";
    }

    @RequestMapping(value = "/group/edit/{groupId}", method = RequestMethod.GET)
    public String group_edit(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        UserGroup userGroup = userGroupDAO.getGroup(groupId);
        model.addAttribute("userGroup", userGroup);
        return "admin/group_edit";
    }

    @RequestMapping(value = "/group/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setCreatedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.insertGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + queryResult.getGeneratedKey();
    }

    @RequestMapping(value = "/group/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(groupId);
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setModifiedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.updateGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.update.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + groupId;
    }

    @RequestMapping(value = "/group/delete/{groupId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("groupId") String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        int userCount = userDAO.getCountByGroupId(groupId);
        if (userCount == 0) {
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            QueryResult queryResult = userGroupDAO.deleteGroup(groupId);
            UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
            QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccessByGroupId(groupId);
            int result = queryResult.getResult() + remQueryResult.getResult();
            if (result != 0) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.delete.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.error", args, locale));
            }
        } else {
            args = new String[]{Integer.toString(userCount)};
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.have_user.error", args, locale));
        }
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(
            Model model
    ) {
        MenuDAO menuDAO = new MenuDAO();
        List<Menu> parentMenuList = menuDAO.getMenuList("0");
        String tbody = "<tbody>";
        String menuOption = "";
        for (int i = 0; i < parentMenuList.size(); i++) {
            Menu parentMenu = parentMenuList.get(i);
            tbody += "<tr><td>&nbsp;</td><td><i class='fa " + parentMenu.getIcon() + "'></i>&nbsp;" + parentMenu.getName() + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
            menuOption += "<option value='" + parentMenu.getId() + "'>" + parentMenu.getName() + "</option>";
            List<Menu> childMenuList = menuDAO.getMenuList(parentMenu.getCode());
            if (!childMenuList.isEmpty()) {
                for (int j = 0; j < childMenuList.size(); j++) {
                    Menu childMenu = childMenuList.get(j);
                    tbody += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td><i class='fa fa-minus'></i>&nbsp;" + childMenu.getName() + "</td><td>&nbsp;</td></tr>";
                    menuOption += "<option value='" + childMenu.getId() + "'>&nbsp;&nbsp;&nbsp;&nbsp;" + childMenu.getName() + "</option>";
                }
            }
        }
        tbody += "</tbody>";
        model.addAttribute("tbody", tbody);
        model.addAttribute("menuOption", menuOption);
        return "admin/menu";
    }
    
}
