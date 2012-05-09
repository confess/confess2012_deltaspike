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
package org.os890.examples.cdi.extensions.backend;

import org.os890.examples.cdi.extensions.domain.User;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//usually such an implementation would be a CDI @Alternative
@ApplicationScoped
public class SimulatedUserService implements UserService
{
    private Map<String, User> userMap = new ConcurrentHashMap<String, User>();

    @Inject
    @HashAlgorithm
    private String algorithm;

    public void registerUser(User user)
    {
        if (user.getUserName() == null || user.getPassword() == null)
        {
            throw new IllegalArgumentException("invalid user");
        }

        user.setPassword(getHashedPassword(user));

        this.userMap.put(user.getUserName(), user);
    }

    public boolean isValid(User user)
    {
        if (user.getUserName() == null || user.getPassword() == null)
        {
            return false;
        }

        User storedUser = this.userMap.get(user.getUserName());

        return storedUser != null && storedUser.getPassword().equals(getHashedPassword(user));
    }

    private String getHashedPassword(User user)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance(this.algorithm);
            md.update(user.getPassword().getBytes());

            byte[] raw = md.digest();
            return new String(raw);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
