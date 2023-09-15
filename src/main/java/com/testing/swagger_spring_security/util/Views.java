package com.testing.swagger_spring_security.util;

public class Views {

	public static interface UserView {
		public static interface Request {
		}

		public static interface Response extends Request {
		}
		
		public static interface UpdateRequest extends Request {
		}
	}

}
