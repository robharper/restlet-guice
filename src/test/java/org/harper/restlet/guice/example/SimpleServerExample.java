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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.harper.restlet.guice.BasicRestletBootstrapModule;
import org.harper.restlet.guice.InjectedRestletServletBridge;
import org.restlet.Application;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * An example server that showcases the restlet-guice code.  This example creates a simple
 * Jetty server that directs all requests to the InjectedRestletServletBridge.  The Restlet
 * Application (and associated routes) are provided via Guice.  
 * 
 * What this example shows is how to let Guice create your Restlet server resources and inject
 * all required dependencies for you.  Moreover, resources can be bound to URI patterns within
 * Guice modules allowing for simple and effective modular server construction.  In this case, only
 * a single module and single resource is shown.  See {@link ExampleRoutingModule} for details.
 * 
 * @author robharper
 *
 */
public class SimpleServerExample {

	public static void main(String[] args) throws Exception
    {
		// Setup Guice, add out default module and our example routing module
		Injector injector = Guice.createInjector(
				new BasicRestletBootstrapModule(),
				new ExampleRoutingModule()
		);

		// Create the restlet application
		// Normally Guice would do this when the InjectedRestletServletBridge is instantiated but
		// we're keeping this example simple
		Application restletApp = injector.getInstance(Application.class);
		
		// Create a Jetty server on 8080
        Server server = new Server(8080);
 
        // Have out injected restlet servlet bridge handle all requests
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new InjectedRestletServletBridge(restletApp)),"/*");
 
        // Go!
        server.start();
        server.join();
    }

}
