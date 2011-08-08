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

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Creates a simple restlet application given a context and a router.  This provider
 * lends a little boilerplate code for simple cases that need an application with a 
 * single inbound router.
 * 
 * @author robharper
 *
 */
public class BasicApplicationProvider implements Provider<Application> {
	
	private final Context context;
	private final Router router;
	
	@Inject
	public BasicApplicationProvider(
			Context context, 
			Router router) {
		this.context = context;
		this.router = router;
	}

	public Application get() {
    	Application application = new Application();
        application.setContext(context);
        application.setInboundRoot(router);
        return application;
	}
	
}
