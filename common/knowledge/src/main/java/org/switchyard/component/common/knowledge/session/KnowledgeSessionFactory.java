/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.component.common.knowledge.session;

import java.util.Map;
import java.util.Properties;

import org.switchyard.ServiceDomain;
import org.switchyard.component.common.knowledge.config.model.KnowledgeComponentImplementationModel;
import org.switchyard.component.common.knowledge.config.model.ManifestModel;
import org.switchyard.config.model.resource.ResourcesModel;

/**
 * The main KnowledgeSession factory.
 *
 * @author David Ward &lt;<a href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red Hat Inc.
 */
public abstract class KnowledgeSessionFactory extends KnowledgeDisposer {

    private final KnowledgeComponentImplementationModel _model;
    private final ClassLoader _loader;
    private final ServiceDomain _domain;
    private final Properties _propertyOverrides;

    /**
     * Constructs a new knowledge session factory.
     * @param model the model
     * @param loader the loader
     * @param domain the domain
     * @param propertyOverrides any property overrides
     */
    protected KnowledgeSessionFactory(KnowledgeComponentImplementationModel model, ClassLoader loader, ServiceDomain domain, Properties propertyOverrides) {
        _model = model;
        _loader = loader;
        _domain = domain;
        _propertyOverrides = propertyOverrides;
    }

    /**
     * Gets the model.
     * @return the model
     */
    public KnowledgeComponentImplementationModel getModel() {
        return _model;
    }

    /**
     * Gets the class loader.
     * @return the class loader
     */
    public ClassLoader getLoader() {
        return _loader;
    }

    /**
     * Gets the service domain.
     * @return the service domain
     */
    public ServiceDomain getDomain() {
        return _domain;
    }

    /**
     * Gets the property overrides.
     * @return the property overrides
     */
    public Properties getPropertyOverrides() {
        return _propertyOverrides;
    }

    /**
     * Creates a new stateless session.
     * @return the session
     */
    public abstract KnowledgeSession newStatelessSession();

    /**
     * Creates a new stateful session.
     * @param environmentOverrides any environment overrides
     * @return the session
     */
    public abstract KnowledgeSession newStatefulSession(Map<String, Object> environmentOverrides);

    /**
     * Gets a persistent session.
     * @param environmentOverrides any environment overrides
     * @param sessionId the session id
     * @return the session
     */
    public abstract KnowledgeSession getPersistentSession(Map<String, Object> environmentOverrides, Integer sessionId);

    /**
     * Creates a new session factory.
     * @param model the model
     * @param loader the class loader
     * @param domain the service domain
     * @param propertyOverrides any property overrides
     * @return the session factory
     */
    public static KnowledgeSessionFactory newSessionFactory(KnowledgeComponentImplementationModel model, ClassLoader loader, ServiceDomain domain, Properties propertyOverrides) {
        ManifestModel manifestModel = model.getManifest();
        if (manifestModel != null) {
            if (manifestModel.getContainer() == null) {
                ResourcesModel resourcesModel = manifestModel.getResources();
                if (resourcesModel != null) {
                    return new KnowledgeBaseSessionFactory(model, loader, domain, propertyOverrides);
                }
            }
        }
        return new KnowledgeContainerSessionFactory(model, loader, domain, propertyOverrides);
    }

}
