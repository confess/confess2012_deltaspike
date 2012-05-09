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
package org.os890.examples.cdi.extensions.gui.security;

import org.apache.myfaces.extensions.cdi.core.api.logging.Logger;
import org.os890.examples.cdi.extensions.domain.User;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;

@Named
@SessionScoped
public class CurrentUserManager implements Serializable
{
    private User user = new User();

    private boolean loggedIn = false;

    @Inject
    private Logger logger; //or any logger if used via a static field

    public boolean isCurrentUserLoggedIn()
    {
        return this.loggedIn;
    }

    public User getUser()
    {
        return user;
    }

    protected void onLogin(@Observes LoginEvent loginEvent)
    {
        this.loggedIn = true;
        this.logger.log(Level.FINE, "Login user: " + loginEvent.getUser().getUserName());
    }

    protected void onLogout(@Observes LogoutEvent logoutEvent)
    {
        this.user = new User();
        this.loggedIn = false;

        this.logger.log(Level.FINE, "Logout user: " + logoutEvent.getUser().getUserName());
    }
}
