/*
 * Copyright 2012 The Play! Framework
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
package controllers;

import actions.CurrentUser;
import models.Module;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.modules.moduleRegistrationForm;
import views.html.modules.myModules;

import static actions.CurrentUser.currentUser;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@With(CurrentUser.class)
public class Modules extends Controller {

	public static Result myModules() {
		return ok(myModules.render(currentUser(), currentUser().modules));
	}

	public static Result showModuleRegistrationForm() {
		return ok(moduleRegistrationForm.render(currentUser(), form(Module.class)));
	}

	public static Result submitModuleRegistrationForm() {
		Form<Module> form = form(Module.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(moduleRegistrationForm.render(currentUser(), form));
		} else {
			Module module = form.get();
			User user = currentUser();
			User user1 = user.addModule(module);
			user1.save();
			return myModules();
		}
	}
}
