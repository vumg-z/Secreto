package com.secret.platform.parser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@RestController
public class ParserController {

    @GetMapping("/parser")
    public Triple<Double, Double, Double> parseEquation(@RequestParam String equation) {
        return parseEquationLogic(equation);
    }

    private Triple<Double, Double, Double> parseEquationLogic(String equation) {
        Pattern pattern = Pattern.compile("\\s*([+-]?\\s*\\d*\\.?\\d*\\s*x)?\\s*([+-]?\\s*\\d*\\.?\\d*\\s*y)?\\s*=\\s*([+-]?\\s*\\d*\\.?\\d*)\\s*");
        Matcher matcher = pattern.matcher(equation.trim());

        if (matcher.matches()) {
            String xTerm = matcher.group(1);
            String yTerm = matcher.group(2);
            String c = matcher.group(3);

            double aCoeff = 0.0;
            if (xTerm != null && xTerm.contains("x")) {
                xTerm = xTerm.replaceAll("[^\\d.-]", "");
                aCoeff = xTerm.isEmpty() ? 1.0 : Double.parseDouble(xTerm);
            }

            double bCoeff = 0.0;
            if (yTerm != null && yTerm.contains("y")) {
                yTerm = yTerm.replaceAll("[^\\d.-]", "");
                bCoeff = yTerm.isEmpty() ? 1.0 : Double.parseDouble(yTerm);
            }

            double cCoeff = c != null ? Double.parseDouble(c.replaceAll("[^\\d.-]", "")) : 0.0;

            return new Triple<>(aCoeff, bCoeff, cCoeff);
        } else {
            return null;
        }
    }

    public static class Triple<A, B, C> {
        private A first;
        private B second;
        private C third;

        public Triple(A first, B second, C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        // Getters and Setters for first, second, and third
    }
}
