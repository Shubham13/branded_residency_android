package com.paragon.sensonic.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("ChallengeName")
        @Expose
        private String challengeName;
        @SerializedName("Session")
        @Expose
        private String session;
        @SerializedName("ChallengeParameters")
        @Expose
        private ChallengeParameters challengeParameters;

        public String getChallengeName() {
            return challengeName;
        }

        public void setChallengeName(String challengeName) {
            this.challengeName = challengeName;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public ChallengeParameters getChallengeParameters() {
            return challengeParameters;
        }

        public void setChallengeParameters(ChallengeParameters challengeParameters) {
            this.challengeParameters = challengeParameters;
        }

        public class ChallengeParameters {

            @SerializedName("USERNAME")
            @Expose
            private String username;
            @SerializedName("email")
            @Expose
            private String email;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

        }

    }

}
