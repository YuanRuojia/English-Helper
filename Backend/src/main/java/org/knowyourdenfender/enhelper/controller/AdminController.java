package org.knowyourdenfender.enhelper.controller;

import org.knowyourdenfender.enhelper.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contains entrypoint for administrative request.
 *
 * @author Miris,xxx...
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    /*
    @Autowired is not recommended for field injection

    Use constructor injection instead:
    private final AnyService anyService;

    @Autowired
    public AnyController(AnyService anyService){
        this.anyService = anyService;
    }
     */

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}
