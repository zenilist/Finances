package org.finances.common;

public enum State {
    AL {
        @Override
        public String toString() {
            return "Alabama";
        }
    }, KY {
        @Override
        public String toString() {
            return "Kentucky";
        }
    }, OH {
        @Override
        public String toString() {
            return "Ohio";
        }
    }, AK {
        @Override
        public String toString() {
            return "Alaska";
        }
    }, LA {
        @Override
        public String toString() {
            return "Louisiana";
        }
    }, OK {
        @Override
        public String toString() {
            return "Oklahoma";
        }
    }, AZ {
        @Override
        public String toString() {
            return "Arizona";
        }
    }, ME {
        @Override
        public String toString() {
            return "Maine";
        }
    }, OR {
        @Override
        public String toString() {
            return "Oregon";
        }
    }, AR {
        @Override
        public String toString() {
            return "Arkansas";
        }
    }, MD {
        @Override
        public String toString() {
            return "Maryland";
        }
    }, PA {
        @Override
        public String toString() {
            return "Pennsylvania";
        }
    }, AS {
        @Override
        public String toString() {
            return "American Samoa";
        }
    }, MA {
        @Override
        public String toString() {
            return "Massachusetts";
        }
    }, PR {
        @Override
        public String toString() {
            return "Puerto Rico";
        }
    }, CA {
        @Override
        public String toString() {
            return "California";
        }
    }, MI {
        @Override
        public String toString() {
            return "Michigan";
        }
    }, RI {
        @Override
        public String toString() {
            return "Rhode Island";
        }
    }, CO {
        @Override
        public String toString() {
            return "Colorado";
        }
    }, MN {
        @Override
        public String toString() {
            return "Minnesota";
        }
    }, SC {
        @Override
        public String toString() {
            return "South Carolina";
        }
    }, CT {
        @Override
        public String toString() {
            return "Connecticut";
        }
    }, MS {
        @Override
        public String toString() {
            return "Mississippi";
        }
    }, SD {
        @Override
        public String toString() {
            return "South Dakota";
        }
    }, DE {
        @Override
        public String toString() {
            return "Delaware";
        }
    }, MO {
        @Override
        public String toString() {
            return "Missouri";
        }
    }, TN {
        @Override
        public String toString() {
            return "Tennessee";
        }
    }, DC {
        @Override
        public String toString() {
            return "District of Columbia";
        }
    }, MT {
        @Override
        public String toString() {
            return "Montana";
        }
    }, TX {
        @Override
        public String toString() {
            return "Texas";
        }
    }, FL {
        @Override
        public String toString() {
            return "Florida";
        }
    }, NE {
        @Override
        public String toString() {
            return "Nebraska";
        }
    }, TT {
        @Override
        public String toString() {
            return "Trust Territories";
        }
    }, GA {
        @Override
        public String toString() {
            return "Georgia";
        }
    }, NV {
        @Override
        public String toString() {
            return "Nevada";
        }
    }, UT {
        @Override
        public String toString() {
            return "Utah";
        }
    }, GU {
        @Override
        public String toString() {
            return "Guam";
        }
    }, NH {
        @Override
        public String toString() {
            return "New Hampshire";
        }
    }, VT {
        @Override
        public String toString() {
            return "Vermont";
        }
    }, HI {
        @Override
        public String toString() {
            return "Hawaii";
        }
    }, NJ {
        @Override
        public String toString() {
            return "New Jersey";
        }
    }, VA {
        @Override
        public String toString() {
            return "Virginia";
        }
    }, ID {
        @Override
        public String toString() {
            return "Idaho";
        }
    }, NM {
        @Override
        public String toString() {
            return "New Mexico";
        }
    }, VI {
        @Override
        public String toString() {
            return "Virgin Islands";
        }
    }, IL {
        @Override
        public String toString() {
            return "Illinois";
        }
    }, NY {
        @Override
        public String toString() {
            return "New York";
        }
    }, WA {
        @Override
        public String toString() {
            return "Washington";
        }
    }, IN {
        @Override
        public String toString() {
            return "Indiana";
        }
    }, NC {
        @Override
        public String toString() {
            return "North Carolina";
        }
    }, WV {
        @Override
        public String toString() {
            return "West Virginia";
        }
    }, IA {
        @Override
        public String toString() {
            return "Iowa";
        }
    }, ND {
        @Override
        public String toString() {
            return "North Dakota";
        }
    }, WI {
        @Override
        public String toString() {
            return "Wisconsin";
        }
    }, KS {
        @Override
        public String toString() {
            return "Kansas";
        }
    }, MP {
        @Override
        public String toString() {
            return "Northern Mariana Islands";
        }
    }, WY {
        @Override
        public String toString() {
            return "Wyoming";
        }
    };

    public static State getValue(String state) throws IllegalArgumentException {
        if (state.length() == 2)
            return State.valueOf(state.toUpperCase());
        for (State enumState : State.values()) {
            if (enumState.toString().equalsIgnoreCase(state))
                return enumState;
        }
        throw new IllegalArgumentException("Invalid state");
    }
}
