/*
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.switchyard.component.resteasy.composer;

import org.apache.log4j.Logger;
import org.switchyard.Exchange;
import org.switchyard.Message;
import org.switchyard.component.common.composer.BaseMessageComposer;

/**
 * Composes/decomposes RESTEasy messages.
 *
 * @author David Ward &lt;<a href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red Hat Inc.
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2013 Red Hat Inc.
 */
public class RESTEasyMessageComposer extends BaseMessageComposer<RESTEasyBindingData> {

    private static final Logger LOGGER = Logger.getLogger(RESTEasyMessageComposer.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Message compose(RESTEasyBindingData source, Exchange exchange) throws Exception {
        final Message message = exchange.createMessage();
        getContextMapper().mapFrom(source, exchange.getContext(message));
        Object content = null;
        if (source.getParameters().length > 0) {
            content = source.getParameters()[0];
        }
        message.setContent(content);

        if (source.getParameters().length > 1) {
            LOGGER.warn("Default RESTEasy Message Composer doesn't handle multiple input parameters.");
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RESTEasyBindingData decompose(Exchange exchange, RESTEasyBindingData target) throws Exception {
        Message sourceMessage = exchange.getMessage();
        Object content = sourceMessage.getContent();
        target.setOperationName(exchange.getContract().getProviderOperation().getName());
        if (content != null) {
            target.setParameters(new Object[]{content});
        }
        getContextMapper().mapTo(exchange.getContext(), target);
        return target;
    }

}
