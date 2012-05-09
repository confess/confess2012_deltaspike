/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.os890.examples.cdi.extensions.gui.controller;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.apache.myfaces.extensions.cdi.jsf.api.Jsf;
import org.apache.myfaces.extensions.cdi.message.api.MessageContext;
import org.apache.myfaces.extensions.cdi.message.api.payload.MessageSeverity;
import org.os890.examples.cdi.extensions.backend.UserService;
import org.os890.examples.cdi.extensions.domain.User;
import org.os890.examples.cdi.extensions.gui.config.Pages;
import org.os890.examples.cdi.extensions.gui.security.CurrentUserManager;
import org.os890.examples.cdi.extensions.gui.security.LoginEvent;
import org.os890.examples.cdi.extensions.gui.security.LogoutEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewAccessScoped
public class UserController implements Serializable
{
    @Inject
    private UserService userService;

    @Inject
    private CurrentUserManager currentUserManager;

    //re-used for the registration
    private User user = new User();

    @Inject
    private Conversation conversation;

    @Inject
    private
    @Jsf
    MessageContext messageContext;

    @Inject
    private Event<LoginEvent> loginEvent;

    @Inject
    private Event<LogoutEvent> logoutEvent;

    //we can use e.g. MyFaces ExtVal for cross-field validation
    private String repeatedPassword;

    public void register()
    {
        if (this.user.getPassword().equals(this.repeatedPassword))
        {
            this.userService.registerUser(this.user);
            this.conversation.close();

            this.messageContext
                    .message()
                    .text("{successfulRegistration}")
                    .payload(MessageSeverity.INFO) //it's the default -> it's optional
                    .add();
        }
        else
        {
            //only that simple to concentrate on the real topic;
            this.messageContext
                    .message()
                    .text("{invalidRegistration}")
                    .payload(MessageSeverity.ERROR)
                    .add();
        }
    }

    public Class<? extends Pages> getShowLogin()
    {
        return Pages.Login.class;
    }

    public Class<? extends Pages> getShowRegistration()
    {
        return Pages.Registration.class;
    }

    public Class<? extends Pages.Vip> login()
    {
        boolean loginSuccessful = this.userService.isValid(this.currentUserManager.getUser());

        if (!loginSuccessful)
        {
            this.messageContext
                    .message()
                    .text("{invalidLogin}")
                    .payload(MessageSeverity.ERROR)
                    .add();
            return null;
        }

        this.loginEvent.fire(new LoginEvent(this.currentUserManager.getUser()));
        return Pages.Vip.VipContent.class;
    }

    public void logout()
    {
        this.logoutEvent.fire(new LogoutEvent(this.currentUserManager.getUser()));
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getRepeatedPassword()
    {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword)
    {
        this.repeatedPassword = repeatedPassword;
    }
}
