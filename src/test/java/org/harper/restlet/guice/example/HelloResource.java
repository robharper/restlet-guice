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

import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * This simple resource says hello to the name encoded in the URI as defined
 * by the Restlet route URI component "name".
 * 
 * The interesting part of this example resource is that the constructor takes
 * arguments that will be injected automatically using Guice
 * 
 * @author robharper
 *
 */
public class HelloResource extends ServerResource {
	
	private final String greeting;
	private final Logger logger;
	
	/**
	 * Constructs a HelloResource.  Note that we can request that Guice inject
	 * dependencies here (the whole point of this little library)
	 * 
	 * @param greeting the phrase with which to greet our guests
	 * @param logger
	 */
	@Inject
	public HelloResource(@Named("Greeting") String greeting, Logger logger) {
		this.greeting = greeting;
		this.logger = logger;
	}
	
    
	@Get
	public String sayHello() {
		String name = (String) getRequestAttributes().get("name"); 
		
		logger.info("Saying "+greeting+" to "+name+"!");
		
		return greeting + ", " + name + "!";
	}

}
