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
package org.harper.restlet.guice;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.routing.Router;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * This Guice Module sets up basic bindings for everything needed to get started with a basic
 * Restlet application.  Use this module, the RestletServletBridge, plus a set of bindings of 
 * paths to ServerResources to create a basic Restlet server.
 * 
 * @author robharper
 *
 */
public class BasicRestletBootstrapModule extends AbstractModule {

	@Override
	protected void configure() {
		// Bind in an empty context
		bind(Context.class).toInstance(new Context());
		
		// Bind a Guice-specific factory that creates Finders (used by Restlet to create ServerResources)
		// We bind the factory in this way so that Guice creates a factory implementation for us that
		// will hand out GuiceFinder instances
		bind(FinderFactory.class).toProvider(FactoryProvider.newFactory(FinderFactory.class, GuiceFinder.class));
		
		// Bind in a basic application instance
		bind(Application.class).toProvider(BasicApplicationProvider.class);
		
		// Bind in a provider that creates a Router instance from a map of routes to ServerResources
		bind(Router.class).toProvider(RouterProvider.class);

	}

}
