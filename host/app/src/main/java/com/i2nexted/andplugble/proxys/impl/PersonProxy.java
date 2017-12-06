package com.i2nexted.andplugble.proxys.impl;

import com.i2nexted.andplugble.proxys.interfaces.IPerson;

public class PersonProxy implements IPerson {
        private IPerson subject;

        public PersonProxy(IPerson subject) {
            this.subject = subject;
        }

        @Override
        public String doThings(String str) {
            return subject.doThings(str) + ".....";
        }
    }