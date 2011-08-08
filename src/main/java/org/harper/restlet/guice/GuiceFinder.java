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

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;

/**
 * An implementation of the Restlet Finder interface (aka ServerResource factory) that 
 * uses Guice to create the requested instances.  This allows ServerResource subclasses
 * to have their dependencies injected.
 * 
 * GuiceFinder makes use of the AssistedInject Guice feature.  Paired with {@link FinderFactory}
 * Guice will automatically create a factory instance that can create this class given only
 * the ServerResource subclass key.
 * 
 * In Guice 2.0:
 * bind(FinderFactory.class).toProvider(FactoryProvider.newFactory(FinderFactory.class, GuiceFinder.class));
 * 
 * In Guice 3.0:
 * install(new FactoryModuleBuilder().implement(Finder.class, GuiceFinder.class).build(FinderFactory.class));
 * 
 * @author robharper
 *
 */
public class GuiceFinder extends Finder {

	private final Injector injector;
		
	private final Class<? extends ServerResource> key;

	@Inject
	public GuiceFinder(	@Assisted Class<? extends ServerResource> key, Injector injector) {
		this.key = key;
		this.injector = injector;
	}
	
	@Override
	public ServerResource create(Request request, Response response) {
		return injector.getInstance( key );
	}
}
