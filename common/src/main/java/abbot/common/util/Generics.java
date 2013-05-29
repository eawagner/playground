/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package abbot.common.util;


public class Generics {

    /**
     * Provides similar behaviour (superficially) to dynamic_cast in C++.
     *
     * If the object can be cast to the generic type it will be returned, otherwise it will return null.
     *
     * @param o Object to be cast.
     * @param <T> The type of object to be cast to.
     * @return The object cast to the specified type or null if it can't be cast.
     */
    @SuppressWarnings("unchecked")
    public static <T> T dynamicCast(Object o) {
        try {
            if (o != null)
                return (T) o;

        } catch (Throwable ignored) {}

        return null;
    }



}
