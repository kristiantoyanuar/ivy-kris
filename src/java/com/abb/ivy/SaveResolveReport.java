/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.abb.ivy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.ivy.core.event.IvyEvent;
import org.apache.ivy.core.event.IvyListener;
import org.apache.ivy.core.event.resolve.EndResolveEvent;
import org.apache.ivy.core.resolve.IvyNode;
import org.apache.ivy.core.settings.IvySettings;

public class SaveResolveReport implements IvyListener {
    Properties resolverDependencies = new Properties();

    @Override
    public void progress(IvyEvent event) {
        if (event instanceof EndResolveEvent) {
            EndResolveEvent endResolveEvent = (EndResolveEvent) event;
            for (IvyNode dep : endResolveEvent.getReport().getDependencies()) {
                if (dep.getModuleRevision() != null) {
                    resolverDependencies.put(
                        dep.getModuleId().getOrganisation() + "#" + dep.getModuleId().getName(),
                        dep.getModuleRevision().getResolver().getName());
                }
            }

            if (endResolveEvent.getReport().getConfigurationReport("default") != null) {
                IvySettings settings = (IvySettings) endResolveEvent.getReport()
                        .getConfigurationReport("default").getResolveEngine().getSettings();

                try {
                    File saveResultFile = new File(settings.getDefaultCache() + "/"
                            + endResolveEvent.getReport().getModuleDescriptor()
                                    .getModuleRevisionId().getOrganisation()
                            + "#" + endResolveEvent.getReport().getModuleDescriptor()
                                    .getModuleRevisionId().getName()
                            + "-result.properties");

                    resolverDependencies.store(new FileOutputStream(saveResultFile), null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
