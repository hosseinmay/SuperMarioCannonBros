
package Cannon;
//Class for managing player high score information
    class Player {

        private String sName;
        private int nScore;

        public String getName() {
            return sName;
        }

        public int getScore() {
            return nScore;
        }

        public void setName(String _sName) {
            sName = _sName;
        }

        public void setScore(int _nScore) {
            nScore = _nScore;
        }
    }
