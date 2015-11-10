/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.gui;

import java.util.Collections;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ivan
 */

@Getter
@Setter
public class PriceListFilter {
    private String cat;
    private String prod;
    private String priceFrom;
    private String priceTo;
}
