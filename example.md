# All nagios checks for a given application

(let [nagios-checks (<- [?time ?host ?service ?state]
     		    	       (nagios ?nagios)
			      (cmis/is-nagios-service-line? ?nagios)
			      (cmis/nagios-service-checks ?nagios :> ?time ?host ?service ?state _ _ _))]
	(?<- (stdout) [?time ?host ?service ?state]
	     (nagios-checks ?time ?host ?service ?state)
	     (cmis/application-servers ?application ?env ?host)
	     (= ?application "ewafermap")
	     (= ?env "prod")))
