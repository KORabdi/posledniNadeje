

class Controller extends React.Component{
    constructor(prop){
        super(prop);
        this.state = {section: ['login'], class: ""};
        this.props.store.dispatch({type: "setComponent", data: this, name: "controller"});
    }
    render(){ 
        return(
            <div>
                {this.state.section.includes("login") &&  <LoginComponent class={this.state.class} store={this.props.store}/> }
                {this.state.section.includes("main") &&  <MainComponent class={this.state.class} store={this.props.store}/> }
            </div>
        );
    }
}

class LoginComponent extends React.Component{
    constructor(prop){
        super(prop);
        this.state = {error: ""};
        this.props.store.dispatch({type: "setComponent", data: this, name: "login"});
        this.login = this.login.bind(this);
    }
    login(){
        this.props.store.dispatch({type: "login", mail: this.mail.value, pass: this.pass.value});
    }
    render(){
        return(
            <div className={"login "+this.props.class}>
                <h1>Forex Analytics</h1>
                <div className="form" >
                    <form>
                        <label for="name" className="label-name"><input ref={inp => this.mail = inp} placeholder="E-mail" title="Your e-mail address" type="email" className="name" /> </label> <br />
                        <label for="pas" ><input ref={inp => this.pass = inp} placeholder="Password" title="Your password" type="password" className="pas" /></label> <br />
                        {this.state.error != "" && <span className="error">{this.state.error}</span>}<br />
                        <button onClick={() => {this.login()}} type="button" className="btn-log-in">Login</button>
                    </form>
                </div>
            </div>
        );
    }


}

class MainComponent extends React.Component{
    constructor(prop){
        super(prop);
        this.state = {account: this.props.store.getState().account};
        this.props.store.dispatch({type: "setComponent", data: this, name: "main"});
        this.props.store.dispatch({type: "setComponent", data: setInterval(() => {this.props.store.dispatch({type: "changes"})}, 2000), name: "interval"});
        
    }
    render(){
        return(
            <div className={"main "+this.props.class}>
                <div className={this.state.account.previousCash > this.state.account.actualCash ? "login down" : "login up" } >
					<span className="status">{this.state.account.actualCash}</span>
					<div className="form" >
					    
					</div>
                </div>
            </div>
        );
    }


}

function redux(state = {components: {}, account: {actualCash: 1, previousCash: 0} }, action) {
    var newState = Object.assign({}, state);
    switch (action.type) {
        case "setComponent":
            newState.components[action.name] = action.data;
            return newState;
        case "login":
            $.ajax({
                type:"POST",
                cache:false,
                url:"/forex/login",
                data: {mail: action.mail, systempassword: action.pass},
                success: (htm) => {
                    console.log(htm);
                    if(htm.status == 0){
                        newState.account.session = htm.data;
                        console.log(newState.account.session);
                        newState.components.controller.setState({section: ['login'], class: "toLeft"}, () => {setTimeout(() => {newState.components.controller.setState({section: ['main'], class: ""});},1000)});
                    }else {
                        
                        newState.components.login.setState({error: htm.message});
                        newState.components.login.pass.value = "";
                    }
                },
                error: function(e){
                    console.log(e);
                }
            });
            return newState;
        case "changes":
            $.ajax({
                type:"POST",
                cache:false,
                url:"/forex/report",
                data: {iduser: newState.account.session.iduser, idsession: newState.account.session.idsession},
                success: (htm) => {
                    console.log(htm);
                    if(htm.status == 0){
                        newState.account.previousCash = newState.account.actualCash;
                        newState.account.actualCash = htm.data.cash;
                        newState.components.main.setState({account: newState.account});
                    }else if(htm.status > 0) {
                        clearInterval(newState.components.interval);
                        newState.components.controller.setState({section: ['login']});
                        newState.components.login.setState({ class: "", error: htm.message});
                        newState.components.login.pass.value = "";
                    }
                },
                error: function(e){
                    console.log(e);
                }
            });
            return newState;
        default:
            return newState;
  }
}

var Store = Redux.createStore(redux);

ReactDOM.render(<Controller store={Store} />,document.getElementById('react'));

