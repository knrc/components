/*
 * JBoss, Home of Professional Open Source Copyright 2009, Red Hat Middleware
 * LLC, and individual contributors by the @authors tag. See the copyright.txt
 * in the distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package org.switchyard.component.camel.netty.model.v1;

import static junit.framework.Assert.assertEquals;

import org.apache.camel.component.netty.NettyEndpoint;
import org.switchyard.component.camel.config.test.v1.V1BaseCamelServiceBindingModelTest;

/**
 * Test for {@link V1CamelNettyBindingModel} checking if it works with sslContextParametersRef.
 *
 * @author Lukasz Dywicki
 */
public class CamelNettyTcpBindingSslContextParameterRefModelTest extends V1BaseCamelServiceBindingModelTest<V1CamelNettyTcpBindingModel, NettyEndpoint> {

    private static final String CAMEL_XML = "/v1/switchyard-netty-tcp-binding-ssl-config-beans.xml";

    private static final String HOST = "google.com";
    private static final Integer PORT = 10230;
    private static final Boolean SSL = true;
    private static final String SSL_CONTEXT_PARAMETERS_REF = "#sslConfig";

    private static final String CAMEL_URI = "netty:tcp://google.com:10230?" +
        "ssl=true&sslContextParametersRef=#sslConfig";

    public CamelNettyTcpBindingSslContextParameterRefModelTest() {
        super(NettyEndpoint.class, CAMEL_XML);

        setSkipCamelEndpointTesting(true);
    }

    @Override
    protected V1CamelNettyTcpBindingModel createTestModel() {
        return (V1CamelNettyTcpBindingModel) new V1CamelNettyTcpBindingModel()
            .setHost(HOST)
            .setPort(PORT)
            .setSsl(SSL)
            .setSslContextParametersRef(SSL_CONTEXT_PARAMETERS_REF);
    }

    @Override
    protected void createModelAssertions(V1CamelNettyTcpBindingModel model) {
        assertEquals(HOST, model.getHost());
        assertEquals(PORT, model.getPort());
        assertEquals(SSL, model.isSsl());
        assertEquals(SSL_CONTEXT_PARAMETERS_REF, model.getSslContextParametersRef());
    }

    @Override
    protected String createEndpointUri() {
        return CAMEL_URI;
    }
}