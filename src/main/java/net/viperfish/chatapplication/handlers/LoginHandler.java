/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.viperfish.chatapplication.handlers;

import net.viperfish.chatapplication.core.LSStatus;
import net.viperfish.chatapplication.core.LSPayload;
import net.viperfish.chatapplication.core.LSRequest;
import net.viperfish.chatapplication.core.LSStatus;
import net.viperfish.chatapplication.core.RequestHandler;
import net.viperfish.chatapplication.core.User;
import net.viperfish.chatapplication.core.UserDatabase;
import net.viperfish.chatapplication.core.UserRegister;

/**
 *
 * @author sdai
 */
public final class LoginHandler implements RequestHandler {

    private UserDatabase userDB;
    private UserRegister reg;

    public LoginHandler(UserDatabase userDB, UserRegister reg) {
        this.userDB = userDB;
        this.reg = reg;
    }

    @Override
    public void init() {
    }

    @Override
    public LSStatus handleRequest(LSRequest req, LSPayload resp) {
        User u = userDB.findByUsername(req.getSource());
        LSStatus status = new LSStatus();
        if (u == null) {
            status.setStatus(LSStatus.LOGIN_FAIL, "User" + req.getSource() + " not found");
            return status;
        }

        String suppliedCredential = req.getData();
        if (suppliedCredential.equals(u.getCredential())) {
            status.setStatus(LSStatus.SUCCESS);
            reg.register(u.getUsername(), req.getSocket());

        } else {
            status.setStatus(LSStatus.LOGIN_FAIL, "Username or password incorrect");
        }
        return status;
    }

}
