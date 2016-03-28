/*
 * Copyright (C) 2014~2016 dinstone<dinstone@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinstone.jrpc.mina;

import com.dinstone.jrpc.api.Client;
import com.dinstone.jrpc.api.DefaultServiceImporter;
import com.dinstone.jrpc.api.ServiceImporter;
import com.dinstone.jrpc.binding.DefaultReferenceBinding;
import com.dinstone.jrpc.binding.ReferenceBinding;
import com.dinstone.jrpc.mina.transport.MinaConnectionFactory;
import com.dinstone.jrpc.transport.ConnectionFactory;
import com.dinstone.jrpc.transport.TransportConfig;

/**
 * @author guojf
 * @version 1.0.0.2013-5-2
 */
public class MinaClient implements Client {

    private ConnectionFactory connectionFactory;

    private ReferenceBinding referenceBinding;

    private ServiceImporter serviceImporter;

    public MinaClient(final String host, final int port) {
        this(host, port, new TransportConfig());
    }

    public MinaClient(final String host, final int port, TransportConfig config) {
        connectionFactory = new MinaConnectionFactory(config);
        referenceBinding = new DefaultReferenceBinding(host, port);
        serviceImporter = new DefaultServiceImporter(referenceBinding, connectionFactory);
    }

    public MinaClient(String serviceAddresses, TransportConfig config) {
        this(new DefaultReferenceBinding(serviceAddresses), config);
    }

    public MinaClient(ReferenceBinding referenceBinding, TransportConfig config) {
        this.referenceBinding = referenceBinding;
        this.connectionFactory = new MinaConnectionFactory(config);
        this.serviceImporter = new DefaultServiceImporter(referenceBinding, connectionFactory);
    }

    public <T> T getService(Class<T> sic) {
        return serviceImporter.importService(sic);
    }

    public <T> T getService(Class<T> sic, String group) {
        return serviceImporter.importService(sic, group);
    }

    @Override
    public void destroy() {
        serviceImporter.destroy();
        referenceBinding.destroy();
        connectionFactory.destroy();
    }

    public MinaClient setDefaultTimeout(int timeout) {
        serviceImporter.setDefaultTimeout(timeout);
        return this;
    }

}
