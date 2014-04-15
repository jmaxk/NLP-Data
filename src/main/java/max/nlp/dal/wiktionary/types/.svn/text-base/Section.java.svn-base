package max.nlp.dal.wiktionary.types;

import java.util.ArrayList;
import java.util.List;

public class Section {

        private String heading;
        private String content = null;
        private ArrayList<Section> subSections = new ArrayList<Section>();

        public boolean hasSubSections(){
                if (subSections.isEmpty())
                        return true;
                else
                        return false;
        }
        public void addSection(Section s){
                subSections.add(s);
        }
        
        public void removeSection(Section s ){
                int id = subSections.indexOf(s);
                if (id == -1){
                        System.out.println("error");
                }
                else
                        subSections.remove(s);
        }

        public boolean isPOSSection(){
                if (heading.contains("Noun")
                                || heading.contains("Adjective")
                                || heading.contains("Verb")
                                || heading.contains("Prepositional phrase")
                                || heading.contains("Phrase")
                                || heading.contains("Adverb")
                                || heading.contains("Pronoun")
                                || heading.contains("Proper noun")
                                || heading.contains("Interjection")
                                || heading.contains("Conjunction")
                                || heading.contains("Interjection")
                                || heading.contains("Preposition")              
                                || heading.contains("Determiner")
                                || heading.contains("Article")
                                || heading.contains("Idiom")
                                || heading.contains("Proverb")          
                                || heading.contains("Abbreviation")
                                || heading.contains("Postposition")
                                || heading.contains("Acronym")
                                || heading.contains("Expression")
                                ){
                        return true;
                }
                else
                        return false;
        }

        public void addSections(List<Section> sects){
                subSections.addAll(sects);
        }
        
        public ArrayList<Section> getSubSectionsHelper() {
                ArrayList<Section> allSubSections = new ArrayList<Section>();
                if (subSections.isEmpty()){
                        ArrayList<Section> self = new ArrayList<Section>();
                        self.add(this);
                        return self;
                }
                else{
                        for (Section child : subSections){
                                allSubSections.addAll(child.getAllSubSections());
                        }
                        allSubSections.add(this);
                        return allSubSections;
                }
        }
        
        public ArrayList<Section> getAllSubSections() {
                ArrayList<Section> allSubSections = new ArrayList<Section>();
                if (subSections.isEmpty()){
                        ArrayList<Section> self = new ArrayList<Section>();
                        self.add(this);
                        return self;
                }
                else{
                        for (Section child : subSections){
                                allSubSections.addAll(child.getSubSectionsHelper());
                        }
                        return allSubSections;

                }
        }
        
        public ArrayList<Section> getAllSubSectionsInclusive() {
            ArrayList<Section> allSubSections = new ArrayList<Section>();
            if (subSections.isEmpty()){
                    ArrayList<Section> self = new ArrayList<Section>();
                    self.add(this);
                    return self;
            }
            else{
                    for (Section child : subSections){
                            allSubSections.addAll(child.getSubSectionsHelper());
                    }
                    allSubSections.add(this);
                    return allSubSections;

            }
    }
        
        public int countPOSTags() {
                ArrayList<Section> allSubSections = getAllSubSections();
                int total = 0;
                for (Section st : allSubSections){
                        if (st.isPOSSection())
                                total++;
                }
                return total;
        }
        
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((content == null) ? 0 : content.hashCode());
                result = prime * result + ((heading == null) ? 0 : heading.hashCode());
                result = prime * result
                                + ((subSections == null) ? 0 : subSections.hashCode());
                return result;
        }
        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Section other = (Section) obj;
                if (content == null) {
                        if (other.content != null)
                                return false;
                } else if (!content.equals(other.content))
                        return false;
                if (heading == null) {
                        if (other.heading != null)
                                return false;
                } else if (!heading.equals(other.heading))
                        return false;
                if (subSections == null) {
                        if (other.subSections != null)
                                return false;
                } else if (!subSections.equals(other.subSections))
                        return false;
                return true;
        }
        public ArrayList<Section> getSubSections(){
                return this.subSections;
        }

        public void setSubSections(ArrayList<Section> subSections) {
                this.subSections = subSections;
        }

        public String getHeading() {
                return heading;
        }
        public void setHeading(String heading) {
                this.heading = heading;
        }
        public String getContent() {
                return content;
        }
        public void setContent(String content) {
                this.content = content;
        }
        public Section(String heading, String content) {
                super();
                this.heading = heading;
                this.content = content;
        } 
        
        public Section() {

        } 



}