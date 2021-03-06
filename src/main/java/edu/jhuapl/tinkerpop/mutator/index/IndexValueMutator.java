/* Copyright 2014 The Johns Hopkins University Applied Physics Laboratory
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
package edu.jhuapl.tinkerpop.mutator.index;

import org.apache.accumulo.core.data.Mutation;

import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Element;

import edu.jhuapl.tinkerpop.AccumuloByteSerializer;
import edu.jhuapl.tinkerpop.Constants;
import edu.jhuapl.tinkerpop.mutator.Mutator;

/**
 * Mutators for vertex/edge index tables.
 */
public class IndexValueMutator {

  private IndexValueMutator() { }

  public static class Add implements Mutator {

    private final Element element;
    private final String key;
    private final Object value;

    public Add(Element element, String key, Object value) {
      this.element = element;
      this.key = key;
      this.value = value;
    }

    @Override
    public Iterable<Mutation> create() {
      byte[] bytes = AccumuloByteSerializer.serialize(value);
      Mutation m = new Mutation(bytes);
      m.put(key.getBytes(), element.getId().toString()
          .getBytes(), Constants.EMPTY);
      return Lists.newArrayList(m);
    }
  }

  public static class Delete implements Mutator {

    private final Element element;
    private final String key;
    private final Object value;

    public Delete(Element element, String key, Object value) {
      this.element = element;
      this.key = key;
      this.value = value;
    }

    @Override
    public Iterable<Mutation> create() {
      byte[] bytes = AccumuloByteSerializer.serialize(value);
      Mutation m = new Mutation(bytes);
      m.putDelete(key, element.getId().toString());
      return Lists.newArrayList(m);
    }
  }
}
