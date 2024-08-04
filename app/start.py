# start.py

import sys
import os

# Add the current directory to the Python path
sys.path.append(os.path.abspath(os.path.dirname(__file__)))

from scripts import run_all_scripts

if __name__ == "__main__":
    run_all_scripts()
