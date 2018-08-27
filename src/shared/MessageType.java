package shared;

public enum MessageType {
	LOGIN {
		@Override
		public String toString() {
			return "LOGIN";
		}
	}, //
	REGISTER {
		@Override
		public String toString() {
			return "REGISTER";
		}
	}, //

	PRIVATE_MESSAGE {
		@Override
		public String toString() {
			return "PRIVATE MESSAGE";
		}
	}, //
	CONNECTED_USERS {
		@Override
		public String toString() {
			return " CONNECTED USERS";
		}
	}, //
	LOGIN_SUCCESS {
		@Override
		public String toString() {
			return " LOGIN SUCCESS";
		}
	}, //
	REGISTER_SUCCESS {
		@Override
		public String toString() {
			return " REGISTER SUCCESS";
		}
	}, //
	BAD_CREDENTIALS {
		@Override
		public String toString() {
			return "BAD CREDENTIALS";
		}
	}, //
	USER_ALREADY_EXIST {
		@Override
		public String toString() {
			return "USER ALREADY EXIST";
		}
	}, //
	USER_DOES_NOT_EXIST {
		@Override
		public String toString() {
			return "USER DOES NOT EXIST";
		}
	}, //
	USER_IS_NOT_CONNECTED {
		@Override
		public String toString() {
			return "USER IS NOT CONNECTED";
		}
	}, //
	USER_ALREADY_LOGIN {
		@Override
		public String toString() {
			return "USER ALREADY LOGIN";
		}
	},
	NEW_USER_JOIN {
		@Override
		public String toString() {
			return "NEW USER JOIN";
		}
	},
	DISCONNECT {
		@Override
		public String toString() {
			return "HAVE A GOOD day!!!";
		}
	}
}
