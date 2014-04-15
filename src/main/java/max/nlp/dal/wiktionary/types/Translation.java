package max.nlp.dal.wiktionary.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

@Entity("Translation")
public class Translation {
	 @SuppressWarnings("unused")
	@Id   private ObjectId id;

	public Translation(Text source, Text target) {
		this.source = source;
		this.target = target;
	}
	private Text source;
	private Text target;

	@Override
	public String toString() {
		return "Translation [source=" + source + ", target=" + target + ", prob=" + prob
				+ ", entropy=" + entropy + "]";
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	public double getEntropy() {
		return entropy;
	}
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	public Text getSource() {
		return source;
	}
	public void setSource(Text source) {
		this.source = source;
	}
	public Text getTarget() {
		return target;
	}
	public void setTarget(Text target) {
		this.target = target;
	}
	private double prob;
	private double entropy;
	

}
