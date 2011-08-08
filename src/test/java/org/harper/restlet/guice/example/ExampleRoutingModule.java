/**
 * Restlet-Guice Connector
 * 
 * Copyright 2011 Robert Harper. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY ROBERT HARPER ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL ROBERT HARPER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Robert Harper.
 */
package org.harper.restlet.guice.example;

import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;

/**
 * This simple example module creates a single route to the HelloResource and binds a dependency instance
 * for that resource (a greeting phrase to use).  In basic Reslet-Guice usage, modules like this should be all the
 * code you need to bind resource classes to URIs and have those resource instances created (and dependencies
 * injected) by Guice.
 * 
 * @author robharper
 *
 */
public class ExampleRoutingModule extends AbstractModule {

	@Override
	protected void configure() {
		// Create a map multibinder
		// Need some ugliness here to handle the fact that we use generics
		TypeLiteral<String> pathType = new TypeLiteral<String>() {};
		TypeLiteral<Class<? extends ServerResource>> clazzType = new TypeLiteral<Class<? extends ServerResource>>() {};
		MapBinder<String, Class<? extends ServerResource>> resourceBinder = MapBinder.newMapBinder(binder(), pathType, clazzType);
		
		// Bind one example resource
		resourceBinder.addBinding("/greet/{name}").toInstance(HelloResource.class);

		// Bind something that that resource depends on, a greeting:
		bind(String.class).annotatedWith(Names.named("Greeting")).toInstance("Ciao");
	}

}
