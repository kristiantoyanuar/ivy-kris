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
package org.apache.ivy.core.resolve;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.ivy.util.Message;
import org.apache.ivy.util.PropertiesFile;
import org.junit.Before;
import org.junit.Test;

public class AbbResolveTest {
    private static final String PROPERTIES_FILE = "test/abb/buildellipse.properties";

    private Ivy ivy;

    private File serviceEarIvyFile = new File("test/abb/ivy.xml");

    protected ResolveOptions getResolveOptions(String[] confs) {
        return new ResolveOptions().setConfs(confs);
    }

    @Before
    public void setup() throws Exception {
        ivy = Ivy.newInstance();
        ivy.getSettings().addAllVariables(
            new PropertiesFile(new File(PROPERTIES_FILE), "additional properties"));
        ivy.configure(new File("test/abb/ivysettings-bali.xml"));
        ivy.getLoggerEngine().pushLogger(new DefaultMessageLogger(Message.MSG_DEBUG));
    }

    @Test
    public void testResolveJI() throws ParseException, IOException {
        ResolveReport report = ivy.resolve(serviceEarIvyFile,
            getResolveOptions(new String[] {"*"}));
    }

}
