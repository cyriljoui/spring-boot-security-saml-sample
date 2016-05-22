/*
 * Copyright 2015 Vincenzo De Notaris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.vdenotaris.spring.boot.security.saml.web.controllers;

import com.mutum.framework.security.token.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vdenotaris.spring.boot.security.saml.web.stereotypes.CurrentUser;

import java.io.IOException;

@Controller
public class LandingController {

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@RequestMapping("/landing")
	public String landing(@CurrentUser User user, Model model) throws IOException {
		// Create JWT Token (final mutum token)
		String token = tokenAuthenticationService.generateToken(user.getUsername());

		model.addAttribute("username", 	user.getUsername());
		model.addAttribute("token", 	token);

		return "landing";
	}

}
