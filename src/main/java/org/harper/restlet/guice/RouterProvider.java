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

import java.util.Map;
import java.util.logging.Logger;

import org.restlet.Context;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides a Router instance with routes connected to mapped ServerResources.  This provider
 * takes a map of URI end-points patterns to ServerResource classes (not instances).  The ServerResource
 * classes will be dynamically created (using Guice) at request time.
 * 
 * <p>Use the following three lines to access the routing multibinder:</p>
 * <code>
 * TypeLiteral<String> pathType = new TypeLiteral<String>() {};
 * TypeLiteral<Class<? extends ServerResource>> clazzType = new TypeLiteral<Class<? extends ServerResource>>() {};
 * MapBinder<String, Class<? extends ServerResource>> resourceBinder = MapBinder.newMapBinder(binder(), pathType, clazzType);
 * resourceBinder.bind("/my/path").toInstance(MyResource.class);
 * </code>
 * 
 * @author robharper
 *
 */
public class RouterProvider implements Provider<Router> {
	
	private final Context context;
	private final Map<String, Class<? extends ServerResource>> routes;
	private final FinderFactory factory;
	private final Logger logger;
	
	
	@Inject
	public RouterProvider(Context context,
			Map<String,Class<? extends ServerResource>> routes, 
			FinderFactory factory,
			Logger logger) {
		this.context = context;
		this.routes = routes;
		this.factory = factory;
		this.logger = logger;
	}

	
	public Router get() {
		
		Router router = new Router(context);
		
		/*
         * Set binding rules here
         */
        for( String path : routes.keySet() ) {        	
        	logger.info("Binding '" + path + "' to " + routes.get(path).getName());
        	router.attach(path, factory.create(routes.get(path)));
        }
        
        return router;
	}
}
