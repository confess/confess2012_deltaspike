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
package org.os890.examples.cdi.extensions.gui.controller;

import org.apache.myfaces.extensions.cdi.core.api.logging.Logger;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.apache.myfaces.extensions.cdi.jsf.api.config.view.InitView;
import org.apache.myfaces.extensions.cdi.jsf.api.config.view.PostRenderView;
import org.apache.myfaces.extensions.cdi.jsf.api.config.view.PreRenderView;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

//just demonstrates some view-controller callbacks
@Named
@ViewAccessScoped
public class LoginPageController implements Serializable
{
    @Inject
    private Logger logger;

    @InitView
    protected void init()
    {
        this.logger.info(getClass().getName() + " init per view");
    }

    @PreRenderView
    protected void beforeRender()
    {
        this.logger.info(getClass().getName() + " init before rendering");
    }

    @PostRenderView
    protected void afterRender()
    {
        this.logger.info(getClass().getName() + " cleanup after rendering");
    }
}
