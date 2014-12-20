package de.morpheus.chatbot.utility;

import java.lang.annotation.Annotation;

public class UtilityAnnotationValue {

	public static String find(Class<?> classToSearchFor, Class<Annotation> annotationToSearchFor, String attribute) {
        String output = null;
        Annotation annotation = classToSearchFor.getAnnotation(annotationToSearchFor);
        if (annotation != null) {
            try {
                output = (String) annotation.annotationType().getMethod(attribute).invoke(annotation);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } 
        return output;
    }
	
}
