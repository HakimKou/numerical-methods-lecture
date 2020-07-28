package info.quantlab.numericalmethods.lecture.montecarlo;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionFromMersenneRandomNumbers;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
import net.finmath.montecarlo.assetderivativevaluation.models.BlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.montecarlo.model.ProcessModel;
import net.finmath.montecarlo.process.EulerSchemeFromProcessModel;
import net.finmath.montecarlo.process.MonteCarloProcess;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

public class MonteCarloBlackScholesEuropeanOptionValuation {

	public static void main(String[] args) throws CalculationException {
		double initialValue = 100.0;
		double riskFreeRate = 0.05;
		double volatility = 0.20;

		double initialTime = 0.0;
		int numberOfTimeSteps = 10;
		double deltaT = 0.5;

		int numberOfPaths = 2000000;
		int seed = 3141;

		double maturity = 5.0;
		double strike = 105;


		ProcessModel blackScholesModel = new BlackScholesModel(initialValue, riskFreeRate, volatility);


		TimeDiscretization timeDiscretization = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, deltaT);

		BrownianMotion brownianMotion = new BrownianMotionFromMersenneRandomNumbers(timeDiscretization, 1, numberOfPaths, seed);

		MonteCarloProcess process = new EulerSchemeFromProcessModel(blackScholesModel, brownianMotion);


		MonteCarloAssetModel blackScholesMonteCarloModel = new MonteCarloAssetModel(process);



		EuropeanOption option = new EuropeanOption(maturity, strike);

		double value = option.getValue(blackScholesMonteCarloModel);


		double valueAnalytic = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFreeRate, volatility, maturity, strike);
		System.out.println("Monte-Carlo valuation...: " + value);
		System.out.println("Analytic valuation......: " + valueAnalytic);
	}

}
