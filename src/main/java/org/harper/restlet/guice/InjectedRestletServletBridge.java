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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.Application;
import org.restlet.ext.servlet.ServletAdapter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A servlet that redirects all requests to a Restlet Application.  Basic boilerplate.
 * Must either be manually instantiated and provided an Application instance or must be
 * created by Guice with a bound Application provider or instance.
 * 
 * @author robharper
 */
@SuppressWarnings("serial")
@Singleton
public class InjectedRestletServletBridge extends HttpServlet
{    
	private Application app;
	private ServletAdapter adapter;

 
    @Inject
    public InjectedRestletServletBridge(Application app) {
		this.app = app;
	}
    
   
    @Override
    public void init() throws ServletException {       
    	// Create the adapter and bind to the injected application
        adapter = new ServletAdapter(getServletContext());
        adapter.setNext(app);
    }
    

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
        
    	// Allow the Restlet adapter to service all requests
    	adapter.service(request, response);
    }
}
