# scripts/__init__.py

import os
import random


def run_all_scripts():
    from scripts.populate_type_codes import main as populate_type_codes_main
    from scripts.populate_three_options_set_1 import main as populate_three_options_main
    from scripts.create_rate_product_m2 import main as populate_rate_product_m2_main
    from scripts.populate_pricing_codes import main as populate_pricing_codes_main
    from scripts.create_default_location import main as create_default_location_main
    from scripts.class_code import main as create_class_codes_main
    from scripts.add_classes_to_rate_product import main as add_classes_to_rate_product_main
    from scripts.create_priv_codes import main as add_priv_codes_main
    from scripts.add_myweb import main as add_my_web1_main

    from scripts.populate_corporate_account import main as add_web1_cdpi_main
    from scripts.create_locations import main as create_locations_main

    from scripts.class_code import main as create_classes_main
    from scripts.add_classes_to_rate_product import main as add_classes_test_main

    ###

    from scripts.FC01.populate_options import main as populate_fc01_main
    from scripts.FC01.set_rates_for_FC01 import main as set_rates_fc01_main

    from scripts.FC02.populate_options import main as populate_fc02_main
    from scripts.FC02.set_rates_for_FC02 import main as set_rates_fc02_main

    from scripts.FC03.populate_options import main as populate_fc03_main
    from scripts.FC03.set_rates_for_FC03 import main as set_rates_fc03_main

    ### populate rates for options
    from scripts.options.populate_options_rates import main as populate_rates_for_options_main
    from scripts.options.populate_options import main as populate_options_main
    ###

    ### LD1
    from scripts.LDW1.populate_options import main as populate_options_LDW1_main
    from scripts.LDW2.populate_options import main as populate_options_LDW2_main

    from scripts.LDW1.set_rates_for_LDW1 import main as populate_rates_LDW1_main
    from scripts.LDW2.set_rates_for_LDW2 import main as populate_rates_LDW2_main

    ## Fees

    from scripts.FEES.populate_fees import main as populate_fees_main
    from scripts.FEES.populate_fees_rates import main as populate_fees_rates_main

    print("Running populate_type_codes.py...")
    populate_type_codes_main()

    print("Running populate_three_options_set_1.py...")
    populate_three_options_main()


    print("Creating m1 product")
    populate_rate_product_m2_main()

    print("Running populate_pricing_codes.py...")
    populate_pricing_codes_main()

    print("Creating default location...")
    create_default_location_main()

    print("Creating locations...")
    create_locations_main()

    print("Creating class codes...")
    create_class_codes_main()

    print("Adding class codes prices to rate product...")
    add_classes_to_rate_product_main()

    print("Adding priv codes...")
    add_priv_codes_main()

    print("Adding corporate contract myweb1")
    add_my_web1_main()

    print("Adding corporate account web1")
    add_web1_cdpi_main()

    print("Populating options...")
    populate_options_main()

    print("Adding full covers...")
    populate_fc01_main()
    populate_fc02_main()
    populate_fc03_main()

    print("Adding full covers rates...")
    set_rates_fc01_main()
    set_rates_fc02_main()
    set_rates_fc03_main()

    print("Populating options LDW1 and LDW2...")
    populate_options_LDW1_main()
    populate_options_LDW2_main()

    print("Populating rates for options...")
    populate_rates_for_options_main()

    print("Populating rates LDW1...")
    populate_rates_LDW1_main()

    print("Populating rates LDW2...")
    populate_rates_LDW2_main()

    print("Populating fees...")
    populate_fees_main()

    print("Populating fees rates...")
    populate_fees_rates_main()

    print("All scripts executed.")


    files = ["kirby.txt", "kirby_two.txt", "kirby_three.txt", "kirby_four.txt"]

    # Open and read the file
    with open(random.choice(files), 'r') as file:
        contents = file.read()

    print(contents)

