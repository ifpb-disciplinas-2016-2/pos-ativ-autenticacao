package ifpb.pos.ativ2.authagain.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 27/08/2016, 00:13:59
 */
@Value.Immutable
public interface Pair {

    String key();

    Object value();

    static Pair create(String key, String value) {
        return new DefaultPair(key, value);
    }

    class DefaultPair implements Pair {

        private final String key;
        private final String value;

        public DefaultPair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public Object value() {
            return value;
        }

    }
}
