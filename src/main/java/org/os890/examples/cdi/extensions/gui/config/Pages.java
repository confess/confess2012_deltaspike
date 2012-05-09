/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.os890.examples.cdi.extensions.gui.config;

import org.apache.myfaces.extensions.cdi.core.api.config.view.DefaultErrorView;
import org.apache.myfaces.extensions.cdi.core.api.config.view.ViewConfig;
import org.apache.myfaces.extensions.cdi.core.api.security.Secured;
import org.apache.myfaces.extensions.cdi.jsf.api.config.view.Page;
import org.apache.myfaces.extensions.cdi.jsf.api.config.view.PageBean;
import org.os890.examples.cdi.extensions.gui.controller.LoginPageController;
import org.os890.examples.cdi.extensions.gui.security.SimpleUserNameAwareAccessDecisionVoter;

import static org.apache.myfaces.extensions.cdi.jsf.api.config.view.Page.NavigationMode.REDIRECT;

/**
 * Type-safe view-config
 */
@Page(navigation = REDIRECT)
public interface Pages extends ViewConfig
{
    public @Page class Welcome implements Pages {}

    @PageBean(LoginPageController.class)
    public @Page class Login extends DefaultErrorView implements Pages /*just to benefit from the config*/ {}

    public @Page class Registration implements Pages {}

    //could be an interface outside - in this case it also leads to an sub-folder
    @Secured(SimpleUserNameAwareAccessDecisionVoter.class) //right now you can't use @Secured from DeltaSpike
    public interface Vip
    {
        public @Page class VipContent implements Vip, Pages {}
    }
}
