package org.springframework.extensions.surf.util;

/**
* Custom Class to neutralize user input
* MNT-20202 Improper Neutralization of CRLF Sequences in HTTP Headers ('HTTP Response Splitting') CWE ID 113 
* LM_2019-01-30
*/
public class UserInputValidator {

	public static String validateRedirectUrl(String url) {
		
		if (url != null) {
        	//TODO: to externalize characters to remove
        	//https://stackoverflow.com/questions/21993290/how-to-fix-improper-neutralization-of-crlf-sequences-in-http-headers-http-res
			url = url.replace("\r", "")
        			.replace("%0d", "")
		            .replace("%0D", "")
		            .replace("\n", "")
		            .replace("%0a", "")
		            .replace("%0A", "");
		}
		
		return url;
	}
	
}
