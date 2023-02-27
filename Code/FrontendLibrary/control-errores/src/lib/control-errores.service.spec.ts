import { TestBed } from '@angular/core/testing';

import { ControlErroresService } from './control-errores.service';

describe('ControlErroresService', () => {
  let service: ControlErroresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControlErroresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
