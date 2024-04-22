package menu_utilities;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextFieldFilters {

	private DocumentFilter usernameFilter;
	private DocumentFilter passwordFilter;
	private DocumentFilter serverAddressFilter;
	private DocumentFilter numeralOnlyFilter;

	public TextFieldFilters() {
		// FILTER USERNAME INPUT ALLOW ONLY A-Z, a-z, 0-9, - and _
		usernameFilter = new DocumentFilter() {
			@Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isInputValid(string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isInputValid(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isInputValid(String text) {
                String allowedPattern = "^[\\w\\-]+$";
                return text != null && text.matches(allowedPattern);
            }
		};

		// FILTER SERVER ADDRESS TO ALLOW ONLY 0-9 and .
		serverAddressFilter = new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (isInputValid(string)) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isInputValid(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

			private boolean isInputValid(String text) {
				String allowedPattern = "^[0-9.]+$";
				return text != null && text.matches(allowedPattern);
			}
		};

		// guys i cant tell what this one does i should make a comment explaining it
		numeralOnlyFilter = new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (isInputValid(string)) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isInputValid(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

			private boolean isInputValid(String text) {
				String allowedPattern = "^[0-9]+$";
				return text != null && text.matches(allowedPattern);
			}
		};

		// FILTER PASSWORD INPUT TO PREVENT WHITESPACE
		passwordFilter = new DocumentFilter() {
			@Override
	        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (string != null && !string.contains(" ")) {
					super.insertString(fb, offset, string, attr);
	            }
	        }

	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	        	if (text != null && !text.contains(" ")) {
	        		super.replace(fb, offset, length, text, attrs);
	            }
	        }
		};
	}

	public DocumentFilter getUsernameFilter() {
		return usernameFilter;
	}

	public DocumentFilter getServerAddressFilter() {
		return serverAddressFilter;
	}

	public DocumentFilter getNumeralOnlyFilter() {
		return numeralOnlyFilter;
	}

	public DocumentFilter getPasswordFilter() {
		return passwordFilter;
	}

}
