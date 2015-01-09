.PHONY: cmis-core cmis-dashboard cmis-statistics cmis deb

deb: cmis

cmis-core:
	cd cmis-core; lein install

cmis-dashboard: cmis-statistics
	cd cmis-dashboard; lein install

cmis-statistics:
	cd cmis-statistics; lein install

cmis: cmis-core cmis-dashboard cmis-statistics
	cd cmis; lein fatdeb

deploy: deb
	scp cmis/target/cmis_1.2.0_all.deb cmis-test.colo.elex.be:/tmp

