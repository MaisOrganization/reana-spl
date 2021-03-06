package expressionsolver.functions;

import jadd.ADD;
import jadd.JADD;
import jadd.UnrecognizedVariableException;

import org.nfunk.jep.ParseException;


/**
 * @author thiago
 *
 */
public class ADDPower extends org.nfunk.jep.function.Power {

    private JADD jadd;

    public ADDPower(JADD jadd) {
        this.jadd = jadd;
    }

    /* (non-Javadoc)
     * @see org.nfunk.jep.function.Power#power(java.lang.Object, java.lang.Object)
     */
    @Override
    public Object power(Object base, Object exponent) throws ParseException {
        if (base instanceof ADD && exponent instanceof ADD) {
            if (!((ADD) exponent).isConstant()) {
                throw new ParseException("Invalid parameter type. Exponent must be constant.");
            }

            return nTimes((ADD) base, Math.round((double) getExponentDoubleValue((ADD) exponent)));
        }
        throw new ParseException("Invalid parameter type");
    }

	private double getExponentDoubleValue(ADD exponent) {
		double exponentValue = 0;
		try {
		    exponentValue = ((ADD) exponent).eval(new String[]{});
		} catch (UnrecognizedVariableException e) {
		    // Unreachable
		}
		return exponentValue;
	}

    private ADD nTimes(ADD base, long exponentValue) {
        if (exponentValue == 0) {
            return base.ifThenElse(jadd.makeConstant(1),
                                   jadd.makeConstant(0));
        }
        ADD result = base;
        for (int i = 1; i < exponentValue; i++) {
            result = result.times(base);
        }
        return result;
    }

}
