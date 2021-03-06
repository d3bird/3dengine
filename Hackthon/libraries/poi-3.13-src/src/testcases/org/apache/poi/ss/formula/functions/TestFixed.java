/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.ss.formula.functions;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.BoolEval;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.StringEval;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.ErrorConstants;

public final class TestFixed extends TestCase {

    private HSSFCell cell11;
    private HSSFFormulaEvaluator evaluator;

    @Override
    public void setUp() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            HSSFSheet sheet = wb.createSheet("new sheet");
            cell11 = sheet.createRow(0).createCell(0);
            cell11.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            evaluator = new HSSFFormulaEvaluator(wb);
        } finally {
            wb.close();
        }
    }

    public void testValid() {
        // thousands separator
        confirm("FIXED(1234.56789,2,TRUE)", "1234.57");
        confirm("FIXED(1234.56789,2,FALSE)", "1,234.57");
        // rounding
        confirm("FIXED(1.8,0,TRUE)", "2");
        confirm("FIXED(1.2,0,TRUE)", "1");
        confirm("FIXED(1.5,0,TRUE)", "2");
        confirm("FIXED(1,0,TRUE)", "1");
        // fractional digits
        confirm("FIXED(1234.56789,7,TRUE)", "1234.5678900");
        confirm("FIXED(1234.56789,0,TRUE)", "1235");
        confirm("FIXED(1234.56789,-1,TRUE)", "1230");
        // less than three arguments
        confirm("FIXED(1234.56789)", "1,234.57");
        confirm("FIXED(1234.56789,3)", "1,234.568");
        // invalid arguments
        confirmValueError("FIXED(\"invalid\")");
        confirmValueError("FIXED(1,\"invalid\")");
        confirmValueError("FIXED(1,2,\"invalid\")");
        // strange arguments
        confirm("FIXED(1000,2,8)", "1000.00");
        confirm("FIXED(1000,2,0)", "1,000.00");
        // corner cases
        confirm("FIXED(1.23456789012345,15,TRUE)", "1.234567890123450");
        // Seems POI accepts longer numbers than Excel does, excel trims the
        // number to 15 digits and removes the "9" in the formula itself.
        // Not the fault of FIXED though.
        // confirm("FIXED(1.234567890123459,15,TRUE)", "1.234567890123450");
        confirm("FIXED(60,-2,TRUE)", "100");
        confirm("FIXED(10,-2,TRUE)", "0");
        // rounding propagation
        confirm("FIXED(99.9,0,TRUE)", "100");
    }

    public void testOptionalParams() {
        Fixed fixed = new Fixed();
        ValueEval evaluate = fixed.evaluate(0, 0, new NumberEval(1234.56789));
        assertTrue(evaluate instanceof StringEval);
        assertEquals("1,234.57", ((StringEval)evaluate).getStringValue());

        evaluate = fixed.evaluate(0, 0, new NumberEval(1234.56789), new NumberEval(1));
        assertTrue(evaluate instanceof StringEval);
        assertEquals("1,234.6", ((StringEval)evaluate).getStringValue());

        evaluate = fixed.evaluate(0, 0, new NumberEval(1234.56789), new NumberEval(1), BoolEval.TRUE);
        assertTrue(evaluate instanceof StringEval);
        assertEquals("1234.6", ((StringEval)evaluate).getStringValue());

        evaluate = fixed.evaluate(new ValueEval[] {}, 1, 1);
        assertTrue(evaluate instanceof ErrorEval);

        evaluate = fixed.evaluate(new ValueEval[] { new NumberEval(1), new NumberEval(1), new NumberEval(1), new NumberEval(1) }, 1, 1);
        assertTrue(evaluate instanceof ErrorEval);
    }

    private void confirm(String formulaText, String expectedResult) {
        cell11.setCellFormula(formulaText);
        evaluator.clearAllCachedResultValues();
        CellValue cv = evaluator.evaluate(cell11);
        assertEquals("Wrong result type: " + cv.formatAsString(), Cell.CELL_TYPE_STRING, cv.getCellType());
        String actualValue = cv.getStringValue();
        assertEquals(expectedResult, actualValue);
    }

    private void confirmValueError(String formulaText) {
        cell11.setCellFormula(formulaText);
        evaluator.clearAllCachedResultValues();
        CellValue cv = evaluator.evaluate(cell11);
        assertTrue("Wrong result type: " + cv.formatAsString(),
                cv.getCellType() == Cell.CELL_TYPE_ERROR
                && cv.getErrorValue() == ErrorConstants.ERROR_VALUE);
    }
}
