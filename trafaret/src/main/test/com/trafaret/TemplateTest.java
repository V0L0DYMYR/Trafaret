package com.trafaret;

import com.trafaret.template.Trafaret;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TemplateTest {

    @Test
    public void PlaceholdersMoreThanVariables_whenMerge_thenLeaveLastPlaceholdersAsIs() {
        Trafaret template = Trafaret.template("Hello ${name} ${surname}!");

        String greeting2vova = template.merge("Vova");

        assertEquals("Hello Vova ${surname}!", greeting2vova);
    }

    @Test
    public void simplePositiveCase() {
        Trafaret template = Trafaret.template("Hello ${name} ${surname}!");

        String greeting2vova = template.merge("Vova", "Derkach");
        String greeting2pavlo = template.merge("Pavlo", "Polyakov");

        assertEquals("Hello Vova Derkach!", greeting2vova);
        assertEquals("Hello Pavlo Polyakov!", greeting2pavlo);
    }
}
