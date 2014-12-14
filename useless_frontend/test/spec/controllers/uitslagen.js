'use strict';

describe('Controller: UitslagenCtrl', function () {

  // load the controller's module
  beforeEach(module('mswFrontendApp'));

  var UitslagenCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    UitslagenCtrl = $controller('UitslagenCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
